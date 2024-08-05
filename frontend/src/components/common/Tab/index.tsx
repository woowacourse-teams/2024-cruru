import { ReactNode } from 'react';
import S from './style';

interface TabProps {
  children: ReactNode;
}

interface TabItemProps {
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

function TabItem({ label, isActive, name, handleClickTabItem }: TabItemProps) {
  return (
    <S.Tab>
      <S.TabButton
        name={name}
        type="button"
        onClick={handleClickTabItem}
        isActive={isActive}
      >
        {label}
      </S.TabButton>
    </S.Tab>
  );
}

function TabPanel({ isVisible, children }: React.PropsWithChildren<TabPanelProps>) {
  return <S.TabPanel isVisible={isVisible}>{children}</S.TabPanel>;
}

Tab.TabItem = TabItem;
Tab.TabPanel = TabPanel;

export default Tab;
