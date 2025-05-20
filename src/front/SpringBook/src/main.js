import './style.css'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import AntDesignVue from 'ant-design-vue'
import router from '@/router'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
// import locale from 'element-plus/dist/locale/zh-cn.js'

const app = createApp(App);

const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);

app.use(AntDesignVue);
// app.use(ElementPlus, { locale })  
app.use(ElementPlus);
app.use(pinia);
app.use(router);
app.mount('#app');
