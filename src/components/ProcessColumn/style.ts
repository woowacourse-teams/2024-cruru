import styled from '@emotion/styled';

const ProcessWrapper = styled.section`
  width: 400px;
  padding: 48px 32px;
  border-radius: 8px;
  background-color: #dedede;

  color: #606060;
`;

const Header = styled.header`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 36px;
`;

const Title = styled.h2`
  font-weight: 400;
  font-size: 24px;
`;

const OptionButton = styled.button`
  width: 48px;
  height: 48px;
  background: white;
  border-radius: 50%;
  text-align: center;
  display: flex;
  justify-content: center;
  padding: 12px;
  cursor: pointer;
`;

const OptionButtonContainer = styled.div({
  width: '48px',
  height: '48px',
  backgroundColor: 'white',
  borderRadius: '50%',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  padding: '12px',
  cursor: 'pointer',
});

const ApplicantList = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

const S = {
  ProcessWrapper,
  Header,
  Title,
  OptionButton,
  OptionButtonContainer,
  ApplicantList,
};

export default S;
