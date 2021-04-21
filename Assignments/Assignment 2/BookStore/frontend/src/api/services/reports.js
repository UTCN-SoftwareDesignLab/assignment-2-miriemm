import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    pdfReport(){
        return HTTP.get(BASE_URL + "/bookstore/export/PDF", { headers: authHeader(), responseType: 'blob' }).then(
            (response) => {
                return response.data;
            }
        );
    },
    csvReport(){
        return HTTP.get(BASE_URL + "/bookstore/export/CSV", { headers: authHeader(), responseType: 'blob' }).then(
            (response) => {
                return response.data;
            }
        );
    },
};

