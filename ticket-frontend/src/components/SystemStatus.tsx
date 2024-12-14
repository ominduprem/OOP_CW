import styled from 'styled-components';
import { TicketStatus } from '../types';

const StatusCard = styled.div`
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
`;

const StatusGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  margin-top: 1rem;
`;

const StatusItem = styled.div`
  text-align: center;
  padding: 1rem;
  background: #f5f5f5;
  border-radius: 4px;
`;

const Label = styled.div`
  font-size: 0.875rem;
  color: #666;
  margin-bottom: 0.5rem;
`;

const Value = styled.div`
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
`;

interface Props {
  status: TicketStatus;
}

export const SystemStatus = ({ status }: Props) => {
  return (
    <StatusCard>
      <h2>System Status</h2>
      <StatusGrid>
        <StatusItem>
          <Label>Total Tickets</Label>
          <Value>{status.totalTickets}</Value>
        </StatusItem>
        <StatusItem>
          <Label>Available Tickets</Label>
          <Value>{status.availableTickets}</Value>
        </StatusItem>
        <StatusItem>
          <Label>Sold Tickets</Label>
          <Value>{status.soldTickets}</Value>
        </StatusItem>
      </StatusGrid>
    </StatusCard>
  );
};