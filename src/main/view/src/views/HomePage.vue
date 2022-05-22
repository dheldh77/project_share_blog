<template>
    <div class="container">
        <div class="content-row">
            <PostCard />
            <PostCard />
            <PostCard />
            <PostCard />
        </div>
        <div class="content-row">
            <PostCard />
            <PostCard />
            <PostCard />
            <PostCard />
        </div>
        <div class="content-row">
            <PostCard />
            <PostCard />
            <PostCard />
            <PostCard />
        </div>
    </div>
</template>

<script>
import PostCard from '@/components/PostCard'
export default {
    name: 'HomePage',

    components: {
        PostCard,
  },
};
</script>

<style scoped>
    .container {
        max-width: 1300px;
        margin: auto;
    }

    .content-row {
        display: flex;
        padding: 20px 20px 0px 0px;
    }

</style>