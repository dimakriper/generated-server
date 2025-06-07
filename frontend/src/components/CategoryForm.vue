<template>
  <form @submit.prevent="$emit('submit', category)">
    <div class="form-group">
      <label :for="id + '-name'">Name</label>
      <input 
        :id="id + '-name'"
        v-model="category.name"
        type="text"
        required
      >
    </div>
    
    <div class="form-group">
      <label :for="id + '-color'">Color</label>
      <input 
        :id="id + '-color'"
        v-model="category.color"
        type="color"
        required
      >
    </div>

    <div class="form-actions">
      <button type="submit" class="btn-primary">{{ submitLabel }}</button>
      <button type="button" @click="$emit('cancel')" class="btn-secondary">Cancel</button>
    </div>
  </form>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'CategoryForm',
  props: {
    initialValue: {
      type: Object,
      default: () => ({
        name: '',
        color: '#1976d2'
      })
    },
    submitLabel: {
      type: String,
      default: 'Save'
    },
    id: {
      type: String,
      default: 'new'
    }
  },
  emits: ['submit', 'cancel'],
  setup(props) {
    const category = ref({ ...props.initialValue });
    return { category };
  }
};
</script>
