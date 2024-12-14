import { useState } from 'react';
import styled from 'styled-components';
import { TicketConfig } from '../types';

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 500px;
  margin: 0 auto;
  padding: 2rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
`;

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const Label = styled.label`
  font-weight: 500;
  color: #333;
`;

const Input = styled.input`
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  
  &:focus {
    outline: none;
    border-color: #0066cc;
    box-shadow: 0 0 0 2px rgba(0,102,204,0.2);
  }
`;

const Button = styled.button`
  padding: 0.75rem 1.5rem;
  background: #0066cc;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
  
  &:hover {
    background: #0052a3;
  }
  
  &:disabled {
    background: #ccc;
    cursor: not-allowed;
  }
`;

interface Props {
  onSubmit: (config: TicketConfig) => void;
}

export const ConfigForm = ({ onSubmit }: Props) => {
  const [config, setConfig] = useState<TicketConfig>({
    totalTickets: 1000,
    maxCapacity: 100,
    vendorCount: 3,
    customerCount: 5,
    releaseRate: 10,
    purchaseRate: 5
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(config);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setConfig(prev => ({
      ...prev,
      [name]: parseInt(value) || 0
    }));
  };

  return (
    <Form onSubmit={handleSubmit}>
      <h2>System Configuration</h2>
      <FormGroup>
        <Label>Total Tickets</Label>
        <Input
          type="number"
          name="totalTickets"
          value={config.totalTickets}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <FormGroup>
        <Label>Max Capacity</Label>
        <Input
          type="number"
          name="maxCapacity"
          value={config.maxCapacity}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <FormGroup>
        <Label>Vendor Count</Label>
        <Input
          type="number"
          name="vendorCount"
          value={config.vendorCount}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <FormGroup>
        <Label>Customer Count</Label>
        <Input
          type="number"
          name="customerCount"
          value={config.customerCount}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <FormGroup>
        <Label>Release Rate</Label>
        <Input
          type="number"
          name="releaseRate"
          value={config.releaseRate}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <FormGroup>
        <Label>Purchase Rate</Label>
        <Input
          type="number"
          name="purchaseRate"
          value={config.purchaseRate}
          onChange={handleChange}
          min="1"
        />
      </FormGroup>
      <Button type="submit">Save Configuration</Button>
    </Form>
  );
};
