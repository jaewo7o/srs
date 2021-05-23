<template>
    <v-card>
        <v-card-title>
            <v-text-field
                v-model="searchParams.contents"
                label="Contents"
                single-line
                hide-details
                append-icon="mdi-search"
            ></v-text-field>
            <v-btn @click="onClickSearch">Search</v-btn>
        </v-card-title>
        <v-data-table
            :headers="messageDataTable.headers"
            :items="messageDataTable.items"
            :page.sync="page.page"
            :items-per-page="page.size"
            :server-items-length="messageDataTable.totalCount"
        ></v-data-table>
    </v-card>
</template>

<script>
import { getMessages } from '@/api/messages'

export default {
    name: 'Message',
    data() {
        return {
            searchParams: {
                contents: ''
            },
            cachedParams: {},
            messageDataTable: {
                headers: [
                    { text: 'Id', value: 'id', align: 'end' },
                    { text: 'Key', value: 'key', align: 'center' },
                    { text: 'Korean', value: 'contentsKo', align: 'start' },
                    { text: 'English', value: 'contentsEn', align: 'start' }
                ],
                items: [],
                totalCount: 0
            },
            page: {
                size: 2,
                page: 0
            }
        }
    },
    watch: {
        page: {
            deep: true,
            handler(newVal, oldVal) {
                console.log(oldVal, newVal)
                this.fetchData()
            }
        }
    },
    async created() {
        this.onClickSearch()
    },
    methods: {
        onClickSearch() {
            this.page.page = 0
            this.fetchData()
        },
        async fetchData() {
            this.cachedParams = Object.assign(this.searchParams, this.page)
            const result = await getMessages(this.cachedParams)
            this.messageDataTable.items = result.data.content
            this.messageDataTable.totalCount = result.data.totalElements
        }
    }
}
</script>
