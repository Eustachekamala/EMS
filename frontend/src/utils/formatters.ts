import { format } from 'date-fns';

export const formatDate = (date: string | Date): string => {
    if (!date) return '';
    return format(new Date(date), 'MMM dd, yyyy');
};

export const formatDateTime = (date: string | Date): string => {
    if (!date) return '';
    return format(new Date(date), 'MMM dd, yyyy HH:mm');
};

export const formatTime = (time: string | Date): string => {
    if (!time) return '';
    if (typeof time === 'string') {
        // Handle LocalTime format "HH:mm:ss"
        const [hours, minutes] = time.split(':');
        return `${hours}:${minutes}`;
    }
    return format(new Date(time), 'HH:mm');
};

export const formatCurrency = (amount: number | string): string => {
    const num = typeof amount === 'string' ? parseFloat(amount) : amount;
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    }).format(num);
};

export const formatName = (firstName: string, lastName: string): string => {
    if (!firstName || !lastName) return firstName || lastName || 'Unknown';
    return `${firstName} ${lastName}`;
};

export const getInitials = (firstName: string, lastName: string): string => {
    const first = firstName?.charAt(0) || '';
    const last = lastName?.charAt(0) || '';
    return `${first}${last}`.toUpperCase() || 'NA';
};
