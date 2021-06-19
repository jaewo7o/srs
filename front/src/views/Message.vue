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
        <v-card-actions>
            <v-btn @click="onClickNewMessage">New Message</v-btn>
        </v-card-actions>
        <srs-data-table
            title="다국어목록"
            :headers="messageDataTable.headers"
            :items="messageDataTable.items"
            :options.sync="options"
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
        </srs-data-table>
        <v-dialog max-width="600" v-model="isOpenDialog">
            <v-card>
                <v-form>
                    <v-card-title>Message Create</v-card-title>
                    <v-divider></v-divider>
                    <v-card-text>
                        <srs-text-field v-model="form.key" label="Key" />
                        <srs-combobox v-model="form.messageType" label="Type" />
                        <srs-text-field v-model="form.contentsKo" label="Korean" />
                        <srs-text-field v-model="form.contentsEn" label="English" />
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer />
                        <v-btn @click="onClickSaveMessage">Save</v-btn>
                        <v-btn @click="onClickClose">Close</v-btn>
                    </v-card-actions>
                </v-form>
            </v-card>
        </v-dialog>
    </v-card>
</template>

<script>
import { getMessages, createMessage, deleteMessage } from '@/api/messages'
import { getCodes } from '@/api/codes'
import SrsTextField from '@/components/base/SrsTextField.vue'

export default {
    components: { SrsTextField },
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
            isOpenDialog: false,
            form: {
                id: '',
                key: '',
                contentsKo: '',
                contentsEn: ''
            },
            options: {
                page: 1,
                itemsPerPage: 5
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
        const codes = getCodes('CM001')
        console.log(codes)

        this.onClickSearch()
    },
    methods: {
        onClickSearch() {
            this.options.page = 1
            this.cachedParams = Object.assign(this.searchParams, {
                page: this.options.page - 1,
                size: this.options.itemsPerPage
            })

            this.fetchData()
        },
        async onClickSaveMessage() {
            createMessage(this.form)
        },
        onClickClose() {
            this.isOpenDialog = false
        },
        async onClickNewMessage() {
            console.log('====>')
            this.isOpenDialog = true
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
