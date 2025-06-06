<template>
  <div class="transactions">
    <h1>Transactions</h1>
    <div class="transactions-controls">
      <button @click="showNewTransactionForm = true" class="btn-primary">Add Transaction</button>
    </div>

    <div v-if="showNewTransactionForm" class="transaction-form">
      <h3>New Transaction</h3>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="description">Description</label>
          <input 
            id="description"
            v-model="newTransaction.description"
            type="text"
            required
          >
        </div>
        
        <div class="form-group">
          <label for="amount">Amount</label>
          <input 
            id="amount"
            v-model.number="newTransaction.amount"
            type="number"
            step="0.01"
            required
          >
        </div>

        <div class="form-group">
          <label for="type">Type</label>
          <select 
            id="type"
            v-model="newTransaction.type"
            required
          >
            <option value="INCOME">Income</option>
            <option value="EXPENSE">Expense</option>
          </select>
        </div>

        <div class="form-group">
          <label for="category">Category</label>
          <select 
            id="category"
            v-model="newTransaction.categoryId"
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
          <label for="date">Date</label>
          <input 
            id="date"
            v-model="newTransaction.date"
            type="date"
            required
          >
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Save</button>
          <button type="button" @click="showNewTransactionForm = false" class="btn-secondary">Cancel</button>
        </div>
      </form>
    </div>

    <div class="transactions-list">
      <div v-for="transaction in transactions" :key="transaction.id" class="transaction-item">
        <div class="transaction-info">
          <span class="description">{{ transaction.description }}</span>
          <span :class="['amount', transaction.type.toLowerCase()]">
            {{ formatCurrency(transaction.amount) }}
          </span>
        </div>
        <div class="transaction-meta">
          <span class="date">{{ formatDate(transaction.date) }}</span>
          <span v-if="transaction.category" class="category" :style="{ backgroundColor: transaction.category.color }">
            {{ transaction.category.name }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api } from '@/api/api';

export default {
  name: 'Transactions',
  setup() {
    const transactions = ref([]);
    const categories = ref([]);
    const showNewTransactionForm = ref(false);
    const newTransaction = ref({
      description: '',
      amount: null,
      type: 'EXPENSE',
      categoryId: '',
      date: new Date().toISOString().split('T')[0]
    });

    const fetchTransactions = async () => {
      try {
        const data = await api.getTransactions();
        transactions.value = data;
      } catch (error) {
        console.error('Error fetching transactions:', error);
      }
    };

    const fetchCategories = async () => {
      try {
        const data = await api.getCategories();
        categories.value = data;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    const handleSubmit = async () => {
      try {
        await api.createTransaction(newTransaction.value);
        showNewTransactionForm.value = false;
        newTransaction.value = {
          description: '',
          amount: null,
          type: 'EXPENSE',
          categoryId: '',
          date: new Date().toISOString().split('T')[0]
        };
        await fetchTransactions();
      } catch (error) {
        console.error('Error creating transaction:', error);
      }
    };

    const formatCurrency = (amount) => {
      return new Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB'
      }).format(amount);
    };

    const formatDate = (date) => {
      return new Date(date).toLocaleDateString('ru-RU');
    };

    onMounted(() => {
      fetchTransactions();
      fetchCategories();
    });

    return {
      transactions,
      categories,
      showNewTransactionForm,
      newTransaction,
      handleSubmit,
      formatCurrency,
      formatDate
    };
  }
};
</script>

<style scoped>
.transactions {
  padding: 20px;
}

.transactions-controls {
  margin-bottom: 20px;
}

.transaction-form {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.btn-primary,
.btn-secondary {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background: #1976d2;
  color: white;
}

.btn-secondary {
  background: #e0e0e0;
  color: #333;
}

.transactions-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.transaction-item {
  background: white;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.transaction-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.description {
  font-weight: 500;
}

.amount {
  font-weight: bold;
}

.amount.income {
  color: #4caf50;
}

.amount.expense {
  color: #f44336;
}

.transaction-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.9em;
  color: #666;
}

.category {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 0.8em;
  color: white;
}
</style>
