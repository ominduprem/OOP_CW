// src/services/api.ts
import axios from 'axios';
import { TicketConfig } from '../types';

const BASE_URL = 'http://localhost:8080/api';

export const api = {
    saveConfig: async (config: TicketConfig) => {
        try {
            const response = await axios.post(`${BASE_URL}/config`, config);
            return response.data;
        } catch (error) {
            console.error('Error saving config:', error);
            throw error;
        }
    },

    getCurrentConfig: async () => {
        try {
            const response = await axios.get(`${BASE_URL}/config/current`);
            return response.data;
        } catch (error) {
            console.error('Error fetching config:', error);
            throw error;
        }
    }
};