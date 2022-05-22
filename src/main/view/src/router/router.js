import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: "/",
        name: 'HomePage',
        component: () => import('@/views/HomePage')
    },
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});