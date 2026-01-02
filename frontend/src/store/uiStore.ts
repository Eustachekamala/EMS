import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface UIState {
    enterpriseName: string;
    setEnterpriseName: (name: string) => void;
}

export const useUIStore = create<UIState>()(
    persist(
        (set) => ({
            enterpriseName: 'EMS Portal',
            setEnterpriseName: (name) => set({ enterpriseName: name }),
        }),
        {
            name: 'ems-ui-storage',
        }
    )
);
