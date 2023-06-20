<template>
  <div id="app">
    <label>Title: </label>
    <input type="text" placeholder="Title 입력" v-model="title" />

    <label>Content: </label>
    <input type="text" placeholder="Content 입력" v-model="content" />

    <button type="submit" @click="sendMessage()">전송</button>

    <div>
      <label>구독 메시지</label>
      <span v-html="subscribeMessage"></span>
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
      title: "",
      content: "",
      subscribeMessage: "",
    };
  },
  created() {
    this.connect();
  },
  methods: {
    sendMessage() {
      if (this.publishTitle !== "" && this.publishContent !== "") {
        this.send();
      }
    },
    send() {
      axios
        .post("http://localhost:9091/send/message", {
          title: this.title,
          content: this.content,
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
            this.subscribeMessage = this.subscribeMessage + "<br/> title: " + body.title + ", contect: " + body.content;
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
label {
  margin-left: 5px;
}
button {
  margin-left: 5px;
}
div {
  margin-top: 10px;
}
</style>
