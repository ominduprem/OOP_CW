import { useEffect, useState } from 'react';
import styled, { createGlobalStyle } from 'styled-components';
import { ConfigForm } from './components/ConfigForm';
import { SystemStatus } from './components/SystemStatus';
import { ActivityLog } from './components/ActivityLog';
import { api } from './services/api';
import { websocketService } from './services/websocket';
import { TicketConfig, TicketStatus, ActivityLog as ActivityLogType, WebSocketMessage } from './types';

const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen,
      Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    background: #f5f7fa;
    color: #333;
    line-height: 1.5;
  }

  h2 {
    margin-bottom: 1.5rem;
    color: #1a1a1a;
  }
`;

const Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
`;

const Header = styled.header`
  text-align: center;
  margin-bottom: 2rem;
`;

const Title = styled.h1`
  font-size: 2.5rem;
  color: #1a1a1a;
  margin-bottom: 1rem;
`;

const Controls = styled.div`
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-bottom: 2rem;
`;

const Button = styled.button<{ variant?: 'start' | 'stop' }>`
  padding: 0.75rem 1.5rem;
  background: ${props => props.variant === 'stop' ? '#dc3545' : '#28a745'};
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
  
  &:hover {
    background: ${props => props.variant === 'stop' ? '#c82333' : '#218838'};
  }
  
  &:disabled {
    background: #ccc;
    cursor: not-allowed;
  }
`;

const Grid = styled.div`
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 2rem;
  margin-top: 2rem;
  
  @media (max-width: 1024px) {
    grid-template-columns: 1fr;
  }
`;

export const App = () => {
  const [isRunning, setIsRunning] = useState(false);
  const [status, setStatus] = useState<TicketStatus>({
    totalTickets: 0,
    availableTickets: 0,
    soldTickets: 0,
    timestamp: new Date().toISOString()
  });
  const [logs, setLogs] = useState<ActivityLogType[]>([]);

  useEffect(() => {
    websocketService.connect();
    
    const handleMessage = (message: WebSocketMessage) => {
      if (message.type === 'STATUS') {
        setStatus(message.payload as TicketStatus);
      } else if (message.type === 'ACTIVITY') {
        setLogs(prev => [...prev, message.payload as ActivityLogType]);
      }
    };
    
    websocketService.addMessageHandler(handleMessage);
    
    return () => {websocketService.removeMessageHandler(handleMessage);
    };
  }, []);

  const handleConfigSubmit = async (config: TicketConfig) => {
    try {
      await api.saveConfig(config);
      alert('Configuration saved successfully');
    } catch (error) {
      alert('Failed to save configuration');
      console.error(error);
    }
  };

  const handleStart = async () => {
    try {
      await api.startSystem();
      setIsRunning(true);
    } catch (error) {
      alert('Failed to start system');
      console.error(error);
    }
  };

  const handleStop = async () => {
    try {
      await api.stopSystem();
      setIsRunning(false);
    } catch (error) {
      alert('Failed to stop system');
      console.error(error);
    }
  };

  return (
    <>
      <GlobalStyle />
      <Container>
        <Header>
          <Title>Ticket Distribution System</Title>
          <Controls>
            <Button
              onClick={handleStart}
              disabled={isRunning}
            >
              Start System
            </Button>
            <Button
              variant="stop"
              onClick={handleStop}
              disabled={!isRunning}
            >
              Stop System
            </Button>
          </Controls>
        </Header>

        <ConfigForm onSubmit={handleConfigSubmit} />
        
        <Grid>
          <div>
            <SystemStatus status={status} />
            <ActivityLog logs={logs} />
          </div>
        </Grid>
      </Container>
    </>
  );
};