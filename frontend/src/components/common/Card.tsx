import type { ReactNode } from 'react';
import { cn } from '@/utils/cn';

interface CardProps {
    children: ReactNode;
    className?: string;
    hover?: boolean;
    onClick?: () => void;
}

export default function Card({ children, className, hover = false, onClick }: CardProps) {
    return (
        <div
            className={cn(
                'glass-card rounded-xl p-6',
                hover && 'glass-hover cursor-pointer',
                onClick && 'cursor-pointer',
                className
            )}
            onClick={onClick}
        >
            {children}
        </div>
    );
}
