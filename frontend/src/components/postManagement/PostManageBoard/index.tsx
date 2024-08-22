import DateInput from '@components/common/DateInput';
import usePostManagement from '@hooks/usePostManagement';
import formatDate from '@utils/formatDate';
import S from './style';

interface PostManageBoardProps {
  dashboardId: string;
  postId: string;
}

export default function PostManageBoard({ dashboardId, postId }: PostManageBoardProps) {
  console.log(dashboardId, postId);

  const { isLoading, postState, setPostState } = usePostManagement({ postId });
  const startDateText = postState ? formatDate(postState.startDate) : '';
  const endDateText = postState ? formatDate(postState.endDate) : '';

  if (isLoading || !postState) {
    return <div>로딩 중입니다...</div>;
  }

  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPostState((prev) => ({
      ...prev,
      startDate: new Date(e.target.value).toISOString(),
    }));
  };

  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPostState((prev) => ({
      ...prev,
      endDate: new Date(e.target.value).toISOString(),
    }));
  };

  return (
    <S.Wrapper>
      <S.Section>
        <S.SectionTitleContainer>
          <h2>모집일자</h2>
          <span>정해진 날짜가 지나면 자동으로 공고가 마감되며 비활성화 처리됩니다.</span>
        </S.SectionTitleContainer>
        <S.DatePickerContainer>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="시작일"
              min={postState.startDate.split('T')[0]}
              max={postState.endDate.split('T')[0]}
              innerText={startDateText}
              onChange={handleStartDate}
            />
          </S.DatePickerBox>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="종료일"
              min={postState.startDate.split('T')[0]}
              disabled={!startDateText}
              innerText={endDateText}
              onChange={handleEndDate}
            />
          </S.DatePickerBox>
        </S.DatePickerContainer>
      </S.Section>
    </S.Wrapper>
  );
}
