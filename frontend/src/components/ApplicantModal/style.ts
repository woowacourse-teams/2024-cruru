import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const Container = styled.div`
  width: 80vw;
  height: 90vh;

  display: grid;
  grid-template-columns: 4fr 9fr 5fr;
  grid-template-rows: 1fr 1.5fr 1fr 14fr;
  grid-template-areas:
    'header header header'
    'sidebar nav evalHeader'
    'sidebar main asideHeader'
    'sidebar main aside';
`;

const ModalHeader = styled.div`
  grid-area: header;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};

  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const ModalSidebar = styled.div`
  grid-area: sidebar;
  border-right: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
  padding: 3.2rem 1.6rem;
`;

const ModalNav = styled.div`
  grid-area: nav;
  border-right: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const ModalNavHeaderContainer = styled.div`
  height: 100%;
  padding: 1.8rem 1.6rem;

  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const ModalNavHeader = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;

  ${({ theme }) => theme.typography.heading[600]};
  color: ${({ theme }) => theme.colors.text.default};
`;

const ModalNavContent = styled.div`
  ${({ theme }) => theme.typography.common.paragraph};
`;

const ModalMain = styled.div`
  grid-area: main;
  border-right: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
  overflow: auto;
  ${hideScrollBar}
`;

const ModalAsideHeader = styled.div`
  grid-area: asideHeader;
  padding: 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const ModalAside = styled.div`
  grid-area: aside;
  padding: 1.6rem;
  overflow: auto;
`;

const ModalEvalHeader = styled.div`
  grid-area: evalHeader;
  padding: 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const ModalMenus = styled.div`
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const ModalMenusTitle = styled.div`
  height: 2rem;
  color: ${({ theme }) => theme.baseColors.grayscale[500]};
  ${({ theme }) => theme.typography.heading[400]}

  margin-bottom: -0.6rem;
`;

const ModalMenusItem = styled.div<{ isSelected: boolean }>`
  ${({ theme }) => theme.typography.heading[400]}

  display: flex;
  align-items: center;
  gap: 1rem;

  color: ${({ theme, isSelected }) => (isSelected ? theme.baseColors.grayscale[900] : theme.baseColors.grayscale[600])};

  &:hover {
    cursor: pointer;
  }
`;

const S = {
  Container,
  ModalHeader,
  ModalSidebar,
  ModalMenus,
  ModalMenusTitle,
  ModalMenusItem,
  ModalNav,
  ModalNavHeaderContainer,
  ModalNavHeader,
  ModalNavContent,
  ModalMain,
  ModalAsideHeader,
  ModalAside,
  ModalEvalHeader,
};

export default S;
