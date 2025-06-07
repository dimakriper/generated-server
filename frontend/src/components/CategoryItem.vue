<template>
  <div class="category-card" :style="{ borderColor: category.color }">
    <div class="category-name">{{ category.name }}</div>
    <div class="category-actions">
      <button @click="$emit('edit', category)" class="btn-icon">✏️</button>
      <button @click="$emit('delete', category.id)" class="btn-icon">🗑️</button>
      <div class="category-color" :style="{ backgroundColor: category.color }"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CategoryItem',
  props: {
    category: {
      type: Object,
      required: true
    }
  },
  emits: ['edit', 'delete']
};
</script>

<style scoped>
.category-card {
  background: white;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  border-left: 4px solid;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.category-name {
  font-weight: 500;
}

.category-color {
  width: 24px;
  height: 24px;
  border-radius: 50%;
}

.category-actions {
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
