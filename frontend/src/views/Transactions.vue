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
            type="text"
            id="description"
            v-model="newTransaction.description"
            required
          />
        </div>
        <div class="form-group">
          <label for="amount">Amount</label>
          <input
            type="number"
            id="amount"
            v-model="newTransaction.amount"
            required
          />
        </div>
        <div class="form-group">
          <label for="type">Type</label>
          <select id="type" v-model="newTransaction.type" required>
            <option value="EXPENSE">Expense</option>
            <option value="INCOME">Income</option>
          </select>
        </div>
        <div class="form-group">
          <label for="categoryId">Category</label>
          <select id="categoryId" v-model="newTransaction.categoryId" required>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label for="date">Date</label>
          <input
            type="date"
            id="date"
            v-model="newTransaction.date"
            required
          />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-primary">Save</button>
          <button @click="showNewTransactionForm = false" class="btn-secondary">Cancel</button>
        </div>
      </form>
    </div>

    <div class="transactions-list">
      <TransactionItem
        v-for="transaction in transactions"
        :key="transaction.id"
        :transaction="transaction"
        @edit="editTransaction"
        @delete="deleteTransaction"
      />
    </div>

    <div v-if="showEditForm" class="transaction-form">
      <h3>Edit Transaction</h3>
      <form @submit.prevent="handleUpdate">
        <div class="form-group">
          <label for="edit-description">Description</label>
          <input
            type="text"
            id="edit-description"
            v-model="editingTransaction.description"
            required
          />
        </div>
        <div class="form-group">
          <label for="edit-amount">Amount</label>
          <input
            type="number"
            id="edit-amount"
            v-model="editingTransaction.amount"
            required
          />
        </div>
        <div class="form-group">
          <label for="edit-type">Type</label>
          <select id="edit-type" v-model="editingTransaction.type" required>
            <option value="EXPENSE">Expense</option>
            <option value="INCOME">Income</option>
          </select>
        </div>
        <div class="form-group">
          <label for="edit-categoryId">Category</label>
          <select id="edit-categoryId" v-model="editingTransaction.categoryId" required>
            <option v-for="category in categories" :key="category.id" :value="category.id">
              {{ category.name }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label for="edit-date">Date</label>
          <input
            type="date"
            id="edit-date"
            v-model="editingTransaction.date"
            required
          />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-primary">Update</button>
          <button @click="showEditForm = false" class="btn-secondary">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api } from '@/api/api';
import TransactionItem from '@/components/TransactionItem.vue';

export default {
  name: 'Transactions',
  components: {
    TransactionItem
  },
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
    const showEditForm = ref(false);
    const editingTransaction = ref(null);

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

    const editTransaction = (transaction) => {
      editingTransaction.value = { ...transaction };
      showEditForm.value = true;
    };

    const handleUpdate = async () => {
      try {
        await api.updateTransaction(editingTransaction.value.id, editingTransaction.value);
        showEditForm.value = false;
        editingTransaction.value = null;
        await fetchTransactions();
      } catch (error) {
        console.error('Error updating transaction:', error);
      }
    };

    const deleteTransaction = async (id) => {
      if (!confirm('Are you sure you want to delete this transaction?')) return;
      
      try {
        await api.deleteTransaction(id);
        await fetchTransactions();
      } catch (error) {
        console.error('Error deleting transaction:', error);
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
      showEditForm,
      editingTransaction,
      handleSubmit,
      editTransaction,
      handleUpdate,
      deleteTransaction,
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

.transactions-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
