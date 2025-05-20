import { defineStore } from 'pinia';

export const useMainStore = defineStore('main', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    isAuthenticated: !!localStorage.getItem('token'),
    userInfo: JSON.parse(localStorage.getItem('userInfo') || '{}')
  }),
  actions: {
    setToken(data) {
      this.token = data;
      this.isAuthenticated = true;
      this.userInfo = data;
      localStorage.setItem('token', data);
      localStorage.setItem('userInfo', JSON.stringify(data));
    },
    clearAuth() {
      this.token = '';
      this.isAuthenticated = false;
      this.userInfo = {};
      localStorage.removeItem('token');
      localStorage.removeItem('userInfo');
    }
  },
  getters: {
    userRole: (state) => state.userInfo.roleId
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: 'main',
        storage: localStorage,
      },
    ],
  }
});