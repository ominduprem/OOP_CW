export interface TicketConfig {
  totalTickets: number;
  maxCapacity: number;
  vendorCount: number;
  customerCount: number;
  releaseRate: number;
  purchaseRate: number;
}
  
  export interface TicketStatus {
    totalTickets: number;
    availableTickets: number;
    soldTickets: number;
    timestamp: string;
  }
  
  export interface ActivityLog {
    actorType: string;
    actorId: number;
    message: string;
    timestamp: string;
  }
  
  export interface WebSocketMessage {
    type: string;
    payload: TicketStatus | ActivityLog;
  }
  