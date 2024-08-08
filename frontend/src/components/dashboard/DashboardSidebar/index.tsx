import { useNavigate } from 'react-router-dom';
import Accordion from '@components/common/Accordion';
import S from './style';

interface Option {
  text: string;
  isSelected: boolean;
  postId: number;
}

interface DashboardSidebarProps {
  options: Option[];
}

export default function DashboardSidebar({ options }: DashboardSidebarProps) {
  const navigate = useNavigate();

  return (
    <S.Container>
      <S.Logo>ㅋㄹㄹ</S.Logo>
      <S.Contents>
        <Accordion title={<S.Link onClick={() => navigate('/dashboardId/1/posts')}>공고</S.Link>}>
          {options.map(({ text, isSelected, postId }, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <Accordion.ListItem key={index}>
              <S.NavButton
                onClick={() => navigate(`/dashboardId/1/${postId}`)}
                isSelected={isSelected}
              >
                {text}
              </S.NavButton>
            </Accordion.ListItem>
          ))}
        </Accordion>
      </S.Contents>
    </S.Container>
  );
}
