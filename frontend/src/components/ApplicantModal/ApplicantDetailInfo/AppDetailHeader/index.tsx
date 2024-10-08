import S from './style';

interface Tab {
  id: number;
  name: string;
  onClick: () => void;
}

interface AppDetailHeaderProps {
  headerTabs: Tab[];
  activeTabId: number;
  content: string;
}

export default function AppDetailHeader({ headerTabs, activeTabId, content }: AppDetailHeaderProps) {
  return (
    <S.Container>
      <S.Header>
        {headerTabs.map(({ id, name, onClick }) => (
          <S.Tab
            key={id}
            active={id === activeTabId}
            onClick={onClick}
          >
            {name}
          </S.Tab>
        ))}
      </S.Header>
      <S.Content>{content}</S.Content>
    </S.Container>
  );
}
