import styled from '@emotion/styled';

const PopoverWrapper = styled.div`
  position: absolute;
  z-index: 1000;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
`;

// Add PopoverWrapper to your S object
const S = {
  // ... existing styles
  PopoverWrapper,
};

export default S;
