<template>
  <div id="app">
    <div>
      <p>발행</p>
      <p>Title: <input v-model="publishTitle" type="text" /></p>
      <p>Content: <input v-model="publishContent" type="text" /></p>
      <p><button @click="sendMessage()">전송</button></p>
    </div>
    <div>
      <p>구독</p>
      <p>Title: {{ subscribeTitle }}</p>
      <p>Content: {{ subscribeContent }}</p>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      publishTitle: "",
      publishContent: "",
      subscribeTitle: "",
      subscribeContent: "",
    };
  },
  created() {
    this.connect();
  },
  methods: {
    sendMessage() {
      if (this.publishTitle !== "" && this.publishContent !== "") {
        this.send();
        this.publishTitle = "";
        this.publishContent = "";
      }
    },
    send() {
      console.log("title:" + this.publishTitle + ", Content: " + this.publishContent);
      axios
        .post("http://localhost:9091/send/message", {
          title: this.publishTitle,
          content: this.publishContent,
        })
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    connect() {
      const serverURL = "http://localhost:9091/ws";

      const vm = this;
      let socket = new SockJS(serverURL);
      vm.stompClient = Stomp.over(socket);
      vm.stompClient.reconnect_delay = 5000;
      vm.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          vm.stompClient.subscribe("/queue/sample-queue", (response) => {
            console.log("구독으로 받은 메시지", response.body);
            let body = JSON.parse(response.body);
            this.subscribeTitle = body.title;
            this.subscribeContent = body.content;
          });
        },
        (error) => {
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
