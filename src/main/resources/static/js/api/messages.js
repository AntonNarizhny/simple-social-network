import Vue from 'vue'

const messages = Vue.resource('/messages{/id}')

export default {
    add: message => messages.save({}, message),
    update: message => messages.update({id: message.id}, message),
    remove: id => messages.remove({id}),
    page: page => Vue.http.get('/messages', {params: { page }})
}