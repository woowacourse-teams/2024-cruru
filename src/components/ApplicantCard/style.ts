import styled from '@emotion/styled';

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

const DropdownDefault = styled.div`
  padding: 8px 16px;
  font-size: 16px;
  width: 120px;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid grey;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const S = {
  CardContainer,
  CardDetail,
  CardHeader,
  CardDate,
  DropdownWrapper,
  DropdownDefault,
};

export default S;
