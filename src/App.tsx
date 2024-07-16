import KanbanBoard from './components/KanbanBoard';
import processMockData from './mocks/processMockData';

export default function App() {
  return <KanbanBoard processes={processMockData} />;
}
