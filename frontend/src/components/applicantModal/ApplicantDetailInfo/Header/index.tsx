import S from './style';

interface Tab {
  id: number;
  name: string;
  onClick: () => void;
}

interface HeaderTabContentProps {
  headerTabs: Tab[];
  activeTabId: number;
  content: string;
}

export default function HeaderTabContent({ headerTabs, activeTabId, content }: HeaderTabContentProps) {
  return (
    <S.Container>
      <S.Header>
        {headerTabs.map(({ id, name, onClick }) => (
          <S.Tab
            // TODO: key값 고치기
            // eslint-disable-next-line react/no-array-index-key
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
