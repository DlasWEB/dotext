function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return - 1;
}

var blockApi = Vue.resource('/api/text{/id}');

Vue.component('blockForm', {
    props : ['blocksWithText', 'blockAttr'],
    data: function() {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        blockAttr: function(newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
                '<input type="text" placeholder="Поделись, чем-нибудь" v-model="text" />' +
                '<input type="button" value="Поделиться" @click="save" />' +
                '</div>',
    methods: {
        save: function() {
            var block = { text: this.text };
            if (this.id) {
                blockApi.update({id : this.id}, block).then(result =>
                result.json().then(data => {
                        var index = getIndex(this.blocksWithText, data.id);
                        this.blocksWithText.splice(index, 1, data);
                        this.text = '';
                        this.id = '';
                    })
                )
            } else {
                blockApi.save({}, block).then(result =>
                    result.json().then(data => {
                        this.blocksWithText.push(data);
                        this.text = '';
                    })
                )
            }
        }
    }
});

Vue.component('blockRow', {
    props: ['block', 'editMethod', 'blocksWithText'],
    template: '<div>' +
                '<i>{{ block.id }}</i> {{ block.text }}' +
                '<span style="position: absolute; right: 0">' +
                    '<input type="button" value="Редактировать" @click="edit" /><input type="button" value="X" @click="del" />' +
                '</span>' +
              '</div>',
    methods : {
        edit: function() {
            this.editMethod(this.block);
        },
        del: function() {
            blockApi.remove({id: this.block.id}).then(result => {
                if (result.ok) {
                    this.blocksWithText.splice(this.blocksWithText.indexOf(this.block), 1)
                }
            });
        }
    }
});

Vue.component('blocksWithTextList', {
    props: ['blocksWithText'],
    data: function() {
        return {
            block: null
        }
    },
    template: '<div style="position: relative; width: 300px;">' +
                '<blockForm :blocksWithText="blocksWithText" :blockAttr="block"/>' +
                '<blockRow v-for="block in blocksWithText" :key="block.id" :block="block" ' +
                    ':editMethod="editMethod" :blocksWithText="blocksWithText" />' +
              '</div>',
    created: function() {
        blockApi.get().then(result =>
            result.json().then(data =>
                data.forEach(block => this.blocksWithText.push(block))
            )
        )
    },
    methods: {
        editMethod: function(block) {
            this.block = block;
        }
    }
});

var app = new Vue({
    el: '#app',
    template : '<blocksWithTextList :blocksWithText="blocksWithText" />',
    data: {
        blocksWithText: []
    }
});