import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.8rem 0.4rem;

  width: 100%;
  /* cursor: pointer; */ // TODO: 추후에 아코디언이 추가될 경우 변경이 필요합니다.
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  gap: 0.4rem;
  ${({ theme }) => theme.typography.heading[500]}
  width: 80%;
`;

const TitleText = styled.span`
  overflow: hidden;
  text-overflow: ellipsis;
  width: 80%;
  white-space: nowrap;
`;

const List = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  padding: 0 0 0 0.8rem;
`;

const ListItem = styled.li`
  ${({ theme }) => theme.typography.common.block}
  margin-bottom: 0;
`;

const S = {
  Container,
  Header,
  Title,
  TitleText,
  List,
  ListItem,
};

export default S;
