import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: "/",
        name: 'HomePage',
        component: () => import('@/components/HomePage')
    },
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});