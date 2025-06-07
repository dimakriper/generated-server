<template>
  <form @submit.prevent="$emit('submit', transaction)">
    <div class="form-group">
      <label :for="id + '-description'">Description</label>
      <input 
        :id="id + '-description'"
        v-model="transaction.description"
        type="text"
        required
      >
    </div>
    
    <div class="form-group">
      <label :for="id + '-amount'">Amount</label>
      <input 
        :id="id + '-amount'"
        v-model.number="transaction.amount"
        type="number"
        step="0.01"
        required
      >
    </div>

    <div class="form-group">
      <label :for="id + '-type'">Type</label>
      <select 
        :id="id + '-type'"
        v-model="transaction.type"
        required
      >
        <option value="INCOME">Income</option>
        <option value="EXPENSE">Expense</option>
      </select>
    </div>

    <div class="form-group">
      <label :for="id + '-category'">Category</label>
      <select 
        :id="id + '-category'"
        v-model="transaction.categoryId"
      >
        <option value="">No Category</option>
        <option 
          v-for="category in categories" 
          :key="category.id"
          :value="category.id"
        >
          {{ category.name }}
        </option>
      </select>
    </div>

    <div class="form-group">
      <label :for="id + '-date'">Date</label>
      <input 
        :id="id + '-date'"
        v-model="transaction.date"
        type="date"
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
  name: 'TransactionForm',
  props: {
    initialValue: {
      type: Object,
      default: () => ({
        description: '',
        amount: null,
        type: 'EXPENSE',
        categoryId: '',
        date: new Date().toISOString().split('T')[0]
      })
    },
    submitLabel: {
      type: String,
      default: 'Save'
    },
    id: {
      type: String,
      default: 'new'
    },
    categories: {
      type: Array,
      required: true
    }
  },
  emits: ['submit', 'cancel'],
  setup(props) {
    const transaction = ref({ ...props.initialValue });
    return { transaction };
  }
};
</script>
