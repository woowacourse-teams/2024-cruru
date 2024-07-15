import { styled } from 'styled-system/jsx';

const DropdownContainer = styled.div`
  position: relative;
  display: inline-block;
`;

const DropdownButton = styled.button`
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

const DropdownList = styled.ul`
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  margin: 0;
  padding: 0;
  list-style: none;
  border: 1px solid grey;
  border-radius: 4px;
  background-color: white;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`;

const DropdownListItem = styled.li`
  padding: 8px 16px;
  cursor: pointer;
`;

const S = {
  DropdownContainer,
  DropdownButton,
  DropdownList,
  DropdownListItem,
};

export default S;
