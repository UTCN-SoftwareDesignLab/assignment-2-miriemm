<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-col
          cols="2"
          sm="4"
          md="2"
      >
      </v-col>
      <v-text-field v-model="parameter" label="Parameter" />
      <v-btn
          sm="8"
          md="2"
          @click="findBook(parameter)">Find Book</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="books"
    >
      <template v-slot:item.actions="{ item }">
        <v-icon
            color="green"
            small
            class="mr-2"
            @click="sell(item)"
        >
          mdi-cash-multiple
        </v-icon>

      </template>
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "BookEmployee",
  props: {
    parameter: String
  },
  data() {
    return {
      books: [],
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: true,
          value: "title",
        },
        {
          text: "Author",
          sortable: true,
          value: "author",
        },
        {
          text: "Genre",
          sortable: true,
          value: "genre",
        },
        {
          text: "Price",
          value: "price",
        },
        {
          text: "Quantity",
          sortable: false,
          value: "quantity",
        },
        {
          text: "Sell book",
          value: "actions",
        },
      ],
    };
  },
  methods: {
    async findBook(parameter) {
      if(parameter){
        this.books = await api.books.findByMultipleCriteria(parameter);
      }
      else{
        await this.refreshList();
      }

    },
     async sell(book) {
       await api.books.sell(book);
       await this.refreshList();
     },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.books = await api.books.allBooks();
    },
  },
  async created() {
    this.refreshList();
    this.books = await api.books.allBooks();
  },
};
</script>

<style scoped></style>