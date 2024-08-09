import styled from '@emotion/styled';

const Container = styled.div`
  width: 80vw;
  height: 90vh;

  display: grid;
  grid-template-columns: 4fr 9fr 5fr;
  grid-template-rows: 1fr 1.5fr 14fr;
  grid-template-areas:
    'header header header'
    'sidebar nav evalHeader'
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

const ModalMain = styled.div`
  grid-area: main;
  border-right: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[600]};
  overflow: auto;
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
  ModalMain,
  ModalAside,
  ModalEvalHeader,
};

export default S;
