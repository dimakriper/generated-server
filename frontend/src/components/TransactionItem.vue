<template>
  <div class="transaction-item">
    <div class="transaction-info">
      <span class="description">{{ transaction.description }}</span>
      <div class="transaction-actions">
        <button @click="$emit('edit', transaction)" class="btn-icon">✏️</button>
        <button @click="$emit('delete', transaction.id)" class="btn-icon">🗑️</button>
        <span :class="['amount', transaction.type.toLowerCase()]">
          {{ formatCurrency(transaction.amount) }}
        </span>
      </div>
    </div>
    <div class="transaction-meta">
      <span class="date">{{ formatDate(transaction.date) }}</span>
      <span v-if="transaction.category" class="category" :style="{ backgroundColor: transaction.category.color }">
        {{ transaction.category.name }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TransactionItem',
  props: {
    transaction: {
      type: Object,
      required: true
    }
  },
  emits: ['edit', 'delete'],
  setup() {
    const formatCurrency = (amount) => {
      return new Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB'
      }).format(amount);
    };

    const formatDate = (date) => {
      return new Date(date).toLocaleDateString('ru-RU');
    };

    return { formatCurrency, formatDate };
  }
};
</script>

<style scoped>
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

.transaction-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.btn-icon {
  background: none;
  border: none;
  padding: 4px;
  cursor: pointer;
  font-size: 1.2em;
}
</style>
