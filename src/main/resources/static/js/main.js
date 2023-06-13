var blockApi = Vue.resource('/api/text{/id}');

Vue.component('blockForm', {
    props : ['blocksWithText'],
    data: function() {
        return {
            text: ''
        }
    },
    template: '<div>' +
                '<input type="text" placeholder="Поделись, чем-нибудь" v-model="text" />' +
                '<input type="button" value="Поделиться" @click="save" />' +
                '</div>',
    methods: {
        save: function() {
            var block = { text: this.text };
            blockApi.save({}, block).then(result =>
                result.json().then(data => {
                    this.blocksWithText.push(data);
                    this.text = '';
                })
            )
        }
    }
});

Vue.component('blockRow', {
    props: ['block'],
    template: '<div>' +
                '<i>{{ block.id }}</i> {{ block.text }}' +
                '<span>' +
                    '<input type="button" value="Редактировать" @click="edit" />' +
                    '<input type="button" value="Удалить" @click="delete" />' +
                '</span>' +
              '</div>',
    methods : {
        edit: function() {
            block
        }
    }
});

Vue.component('blocksWithTextList', {
    props: ['blocksWithText'],
    template: '<div>' +
                '<blockForm :blocksWithText="blocksWithText" />' +
                '<blockRow v-for="block in blocksWithText" :key="block.id" :block="block"/>' +
              '</div>',
    created: function() {
        blockApi.get().then(result =>
            result.json().then(data =>
                data.forEach(block => this.blocksWithText.push(block))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template : '<blocksWithTextList :blocksWithText="blocksWithText" />',
    data: {
        blocksWithText: []
    }
});