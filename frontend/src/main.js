import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入新字体
import './assets/font/font.css'
//引入ElementUI-plus和ElementUI
import ElementPlus from 'element-plus'
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)
app.use(ElementPlus)
app.use(ElementUI)
app.use(router).use(store).mount("#app")