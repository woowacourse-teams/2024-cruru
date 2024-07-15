import { styled } from 'styled-system/jsx';

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: white;
`;

const CardDetail = styled.div`
  display: flex;
  flex-direction: column;
`;

const CardHeader = styled.div`
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
`;

const CardDate = styled.div`
  font-size: 16px;
  color: grey;
  margin-bottom: 16px;
`;

const DropdownWrapper = styled.div`
  align-self: center;
`;

const S = {
  CardContainer,
  CardDetail,
  CardHeader,
  CardDate,
  DropdownWrapper,
};

export default S;
