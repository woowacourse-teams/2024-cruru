import { ComponentProps, ReactNode } from 'react';
import S from './style';

interface TabProps {
  children: ReactNode;
}

interface TabItemProps extends ComponentProps<'button'> {
  label: string;
  isActive: boolean;
  name: string;
  handleClickTabItem: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

interface TabPanelProps {
  isVisible: boolean;
}

function Tab({ children }: TabProps) {
  return (
    <S.Nav>
      <S.Tabs>{children}</S.Tabs>
    </S.Nav>
  );
}

function TabItem({ label, isActive, name, handleClickTabItem, disabled }: TabItemProps) {
  return (
    <S.Tab>
      <S.TabButton
        name={name}
        type="button"
        onClick={handleClickTabItem}
        isActive={isActive}
        disabled={disabled}
      >
        {label}
      </S.TabButton>
    </S.Tab>
  );
}

function TabPanel({ isVisible, children }: React.PropsWithChildren<TabPanelProps>) {
  return isVisible && <S.TabPanel>{children}</S.TabPanel>;
}

Tab.TabItem = TabItem;
Tab.TabPanel = TabPanel;

export default Tab;
