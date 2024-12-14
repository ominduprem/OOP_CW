import styled from 'styled-components';
import { ActivityLog as ActivityLogType } from '../types';

const LogContainer = styled.div`
  background: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  max-height: 400px;
  overflow-y: auto;
`;

const LogEntry = styled.div`
  padding: 1rem;
  border-bottom: 1px solid #eee;
  
  &:last-child {
    border-bottom: none;
  }
`;

const LogHeader = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
  color: #666;
`;

const LogMessage = styled.div`
  color: #333;
`;

interface ActorTypeProps {
  type: 'Vendor' | 'Customer';
}

const ActorType: React.FC<ActorTypeProps> = ({ type }) => {
  return <span>{type}</span>;
};

interface Props {
  logs: ActivityLogType[];
}

export const ActivityLog = ({ logs }: Props) => {
  return (
    <LogContainer>
      <h2>Activity Log</h2>
      {logs.map((log, index) => {
        const actorType = log.actorType === 'Vendor' || log.actorType === 'Customer' ? log.actorType : 'Customer'; // Default to 'Customer' if not 'Vendor' or 'Customer'
        return (
          <LogEntry key={index}>
            <LogHeader>
              <ActorType type={actorType} />
              {log.actorType} #{log.actorId}
              <span>{new Date(log.timestamp).toLocaleTimeString()}</span>
            </LogHeader>
            <LogMessage>{log.message}</LogMessage>
          </LogEntry>
        );
      })}
    </LogContainer>
  );
};