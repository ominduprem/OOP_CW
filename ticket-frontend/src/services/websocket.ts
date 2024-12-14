import { WebSocketMessage } from '../types';

export class WebSocketService {
  private ws: WebSocket | null = null;
  private messageHandlers: ((message: WebSocketMessage) => void)[] = [];

  connect() {
    this.ws = new WebSocket('ws://localhost:8080/websocket');
    
    this.ws.onmessage = (event) => {
      const message = JSON.parse(event.data);
      this.messageHandlers.forEach(handler => handler(message));
    };
    
    this.ws.onclose = () => {
      setTimeout(() => this.connect(), 5000);
    };
  }

  addMessageHandler(handler: (message: WebSocketMessage) => void) {
    this.messageHandlers.push(handler);
  }

  removeMessageHandler(handler: (message: WebSocketMessage) => void) {
    this.messageHandlers = this.messageHandlers.filter(h => h !== handler);
  }
}

export const websocketService = new WebSocketService();