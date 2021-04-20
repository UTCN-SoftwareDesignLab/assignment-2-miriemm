<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
    >
      <template v-slot:item.actions="{ item }">
        <v-icon
            color="green"
            small
            class="mr-2"
            @click="editBook(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
            color="red"
            small
            @click="deleteBook(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
    <BookDialog
      v-bind:opened="dialogVisible"
      :book="selectedItem"
      @refresh="refreshList"
    ></BookDialog>
    <v-btn @click="removeAllBooks">Remove all</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";

export default {
  name: "BookList",
  components: { BookDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        {
          text: "Author",
          value: "author",
        },
        {
          text: "Genre",
          sortable: false,
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
          text: "Actions",
          value: "actions"}
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedItem = book;
      this.dialogVisible = true;
    },
    async deleteBook(book) {
       await api.books.delete(book);
       this.message = "Delete of book succesful!";
       await this.refreshList();

    },
    addBook() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
