import { ComponentProps, ReactNode } from 'react';
import S from './style';
import CopyToClipboard from '../CopyToClipboard';

interface TabProps {
  children: ReactNode;
  postUrl?: string;
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

function Tab({ children, postUrl }: TabProps) {
  return (
    <S.Nav>
      <S.Tabs>{children}</S.Tabs>
      {postUrl && (
        <S.CopyWrapper>
          <CopyToClipboard url={postUrl} />
        </S.CopyWrapper>
      )}
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
  return <S.TabPanel isVisible={isVisible}>{children}</S.TabPanel>;
}

Tab.TabItem = TabItem;
Tab.TabPanel = TabPanel;

export default Tab;
