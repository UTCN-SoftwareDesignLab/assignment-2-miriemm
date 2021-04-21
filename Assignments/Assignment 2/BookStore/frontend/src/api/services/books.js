import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/bookstore", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(book) {
    return HTTP.patch(BASE_URL + "/bookstore", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(book) {
    return HTTP.delete(BASE_URL + "/bookstore", { data: book, headers: authHeader() }).then(
        (response) => {
      return response.data;
    });
  },
  sell(book) {
    return HTTP.patch(BASE_URL + "/bookstore/sellBook", book, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  findByMultipleCriteria(parameter){
    return HTTP.get(BASE_URL + "/bookstore/findByMultipleCriteria/" + parameter, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
    // pdfReport(){
    //   return HTTP.get(BASE_URL + "/bookstore/export/PDF", { headers: authHeader() }).then(
    //       (response) => {
    //           return response.data;
    //       }
    //       );
    // },
};

