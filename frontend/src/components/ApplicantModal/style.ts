import styled from '@emotion/styled';

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
`;

const ModalAsideHeader = styled.div`
  grid-area: asideHeader;
  padding: 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const ModalAside = styled.div`
  grid-area: aside;
  padding: 1.6rem; //TODO: Refactor
  overflow: auto;
`;

const ModalEvalHeader = styled.div`
  grid-area: evalHeader;
  padding: 1.6rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const S = {
  Container,
  ModalHeader,
  ModalSidebar,
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
