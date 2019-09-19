package view;

import http.ContentType;
import http.request.HttpRequest;
import http.response.DefaultView;
import http.response.View;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultViewTest {
    @Test
    void index문서_get요청_response_body확인() throws IOException, URISyntaxException {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        View view = new DefaultView(httpRequest.getPath());

        assertThat(view.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath()));
    }

    @Test
    void css문서_get요청_response_body_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        View view = new DefaultView(httpRequest.getPath());

        assertThat(view.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath()));
    }

    @Test
    void js문서_get요청_response_body_확인() throws IOException, URISyntaxException {
        String request = "GET /js/bootstrap.min.js HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        View view = new DefaultView(httpRequest.getPath());

        assertThat(view.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath()));
    }

    @Test
    void css문서_get요청_response_header_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        View view = new DefaultView(httpRequest.getPath());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        setHeader(httpRequest, byteArrayOutputStream);

        assertThat(view.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    @Test
    void js문서_get요청_response_header_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        View view = new DefaultView(httpRequest.getPath());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        setHeader(httpRequest, byteArrayOutputStream);

        assertThat(view.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    private void setHeader(HttpRequest httpRequest, ByteArrayOutputStream byteArrayOutputStream) throws IOException, URISyntaxException {
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeBytes("HTTP/1.1 200 OK\r\n");
        dataOutputStream.writeBytes("Content-Type: " + ContentType.valueByPath(httpRequest.getPath()).getContents() + ";charset=utf-8\r\n");
        dataOutputStream.writeBytes("Content-Length: " + FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getPath()).length + "\r\n");
        dataOutputStream.writeBytes("\r\n");
    }

}