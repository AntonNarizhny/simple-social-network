<template>
  <v-layout class="px-5">
    <v-text-field
        label="Оставьте комментарий"
        placeholder="Напишите комментарий"
        v-model="text"
        @keyup.enter="save"
    />
    <v-btn @click="save">
      Оставить комментарий
    </v-btn>
  </v-layout>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  name: 'CommentForm',
  props: ['messageId'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    ...mapActions(['addCommentAction']),
    async save() {
      await this.addCommentAction({
        text: this.text,
        message: {
          id: this.messageId
        }
      })
      this.text = ''
    }
  }
}
</script>

<style scoped>

</style>