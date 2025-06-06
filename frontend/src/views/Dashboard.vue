<template>
  <div class="dashboard">
    <h1>Dashboard</h1>
    <div class="summary">
      <div class="stat-card">
        <h3>Total Income</h3>
        <p>{{ formatCurrency(summary.totalIncome) }}</p>
      </div>
      <div class="stat-card">
        <h3>Total Expense</h3>
        <p>{{ formatCurrency(summary.totalExpense) }}</p>
      </div>
      <div class="stat-card">
        <h3>Balance</h3>
        <p>{{ formatCurrency(summary.balance) }}</p>
      </div>
    </div>
    <div class="recent-transactions">
      <h2>Recent Transactions</h2>
      <div v-if="summary.recentTransactions?.length" class="transactions-list">
        <div v-for="transaction in summary.recentTransactions" :key="transaction.id" class="transaction-item">
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
      <p v-else>No recent transactions</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api } from '@/api/api';

export default {
  name: 'Dashboard',
  setup() {
    const summary = ref({
      totalIncome: 0,
      totalExpense: 0,
      balance: 0,
      recentTransactions: []
    });

    const fetchDashboard = async () => {
      try {
        const data = await api.getDashboard();
        summary.value = data;
      } catch (error) {
        console.error('Error fetching dashboard:', error);
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

    onMounted(fetchDashboard);

    return {
      summary,
      formatCurrency,
      formatDate
    };
  }
};
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.summary {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stat-card h3 {
  margin: 0 0 10px;
  color: #666;
  font-size: 0.9em;
}

.stat-card p {
  margin: 0;
  font-size: 1.5em;
  font-weight: bold;
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
