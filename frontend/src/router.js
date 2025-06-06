import Dashboard from '@/views/Dashboard.vue';
import Transactions from '@/views/Transactions.vue';
import Categories from '@/views/Categories.vue';

export default [
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/transactions',
    name: 'Transactions',
    component: Transactions
  },
  {
    path: '/categories',
    name: 'Categories',
    component: Categories
  }
];
