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
            title="다국어목록"
            :headers="messageDataTable.headers"
            :items="messageDataTable.items"
            :page.sync="page.page"
            :items-per-page="page.size"
            :server-items-length="messageDataTable.totalCount"
        >
            <template v-slot:[`item.update`]="{ item }">
                <v-btn icon @click="onClickUpdateMessage(item)">
                    <v-icon>mdi-pencil</v-icon>
                </v-btn>
            </template>
            <template v-slot:[`item.delete`]="{ item }">
                <v-btn icon @click="onClickDeleteMessage(item)">
                    <v-icon>mdi-delete</v-icon>
                </v-btn>
            </template>
        </v-data-table>
    </v-card>
</template>

<script>
import { getMessages, deleteMessage } from '@/api/messages'

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
                    { text: 'English', value: 'contentsEn', align: 'start' },
                    { text: 'Modify', value: 'update', align: 'center' },
                    { text: 'Delete', value: 'delete', align: 'center' }
                ],
                items: [],
                totalCount: 0
            },
            page: {
                size: 5,
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
            this.cachedParams = Object.assign(this.searchParams, this.page)

            this.fetchData()
        },
        async onClickUpdateMessage(item) {
            console.log(item)
        },
        async onClickDeleteMessage(item) {
            const result = await deleteMessage(item.id)
            if (result.isSuccess) {
                this.fetchData()
            }
        },
        async fetchData() {
            const result = await getMessages(this.cachedParams)

            if (result.isSuccess) {
                this.messageDataTable.items = result.data.content
                this.messageDataTable.totalCount = result.data.totalElements
            }
        }
    }
}
</script>
