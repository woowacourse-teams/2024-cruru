import { useParams } from 'react-router-dom';

import Dropdown from '@components/_common/molecules/Dropdown';
import Button from '@components/_common/atoms/Button';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import specificApplicant from '@hooks/useSpecificApplicant';
import formatDate from '@utils/formatDate';
import { useModal } from '@contexts/ModalContext';
import S from './style';

interface ApplicantBaseInfoProps {
  applicantId: number;
}

export default function ApplicantBaseInfo({ applicantId }: ApplicantBaseInfoProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { mutate: moveApplicantProcess } = useApplicant({ applicantId });
  const { mutate: rejectMutate } = specificApplicant.useRejectApplicant({ dashboardId, applyFormId });
  const { mutate: unrejectMutate } = specificApplicant.useUnrejectApplicant({ dashboardId, applyFormId });
  const { processList, isLoading: isProcessLoading } = useProcess({ dashboardId, applyFormId });
  const { data: applicantBaseInfo, isLoading: isBaseInfoLoading } = specificApplicant.useGetBaseInfo({ applicantId });

  const { close } = useModal();

  if (!applicantBaseInfo) {
    return <div>no data</div>; // TODO: 핸들링
  }

  if (isBaseInfoLoading || isProcessLoading) {
    return <div>Loading...</div>; // TODO: Loading 핸들링
  }

  const { applicant, process } = applicantBaseInfo;

  const items = processList
    .filter(({ processName }) => processName !== process.name)
    .map(({ processId, processName }) => ({
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }: { targetProcessId: number }) => {
        moveApplicantProcess({ processId: targetProcessId, applicants: [applicantId] });
      },
    }));

  const rejectAppHandler = () => {
    const confirmAction = (message: string, action: () => void) => {
      const isConfirmed = window.confirm(message);
      if (isConfirmed) {
        action();
        close();
      }
    };

    if (!applicant.isRejected) {
      confirmAction('정말 해당 지원자를 불합격 하시겠습니까?', () => rejectMutate({ applicantId }));
    } else {
      confirmAction('지원자의 불합격을 취소하시겠습니까?', () => unrejectMutate({ applicantId }));
    }
  };

  return (
    <S.Container>
      <S.Title>{applicant.name}</S.Title>
      <S.ActionRow>
        <Dropdown
          initValue={process.name}
          size="sm"
          items={items}
          width={112}
          isShadow={false}
          disabled={applicant.isRejected}
        />
        <Button
          size="sm"
          color="error"
          onClick={rejectAppHandler}
        >
          {applicant.isRejected ? '불합격 취소' : '불합격'}
        </Button>
      </S.ActionRow>
      <S.DetailContainer>
        <S.DetailRow>
          <S.Label>이메일</S.Label>
          <S.Value>{applicant.email}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>연락처</S.Label>
          <S.Value>{applicant.phone}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>접수일</S.Label>
          <S.Value>{formatDate(applicant.createdAt)}</S.Value>
        </S.DetailRow>
      </S.DetailContainer>
    </S.Container>
  );
}
